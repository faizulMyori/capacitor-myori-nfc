package com.myori.nfc;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefMessage;
import android.nfc.tech.NdefRecord;
import android.util.Log;

import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.annotation.PluginMethod;
import com.getcapacitor.JSObject;

import java.nio.charset.Charset;
import java.util.Arrays;

@CapacitorPlugin(name = "NfcPlugin")
public class NfcPlugin extends Plugin {

    private NfcAdapter nfcAdapter;
    private PluginCall activeCall;

    @Override
    public void load() {
        super.load();
        nfcAdapter = NfcAdapter.getDefaultAdapter(getContext());
        if (nfcAdapter == null) {
            Log.e("NfcPlugin", "NFC is not supported on this device.");
        }
    }

    @PluginMethod
    public void isAvailable(PluginCall call) {
        JSObject response = new JSObject();
        response.put("isAvailable", nfcAdapter != null && nfcAdapter.isEnabled());
        call.resolve(response);
    }

    @PluginMethod
    public void readTag(PluginCall call) {
        if (nfcAdapter == null) {
            call.reject("NFC is not supported on this device.");
            return;
        }

        activeCall = call;
        Intent intent = getActivity().getIntent();
        processNfcIntent(intent);
    }

    private void processNfcIntent(Intent intent) {
        if (intent == null || !NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            if (activeCall != null) {
                activeCall.reject("No NFC tag detected.");
                activeCall = null;
            }
            return;
        }

        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag == null) {
            if (activeCall != null) {
                activeCall.reject("No NFC tag found.");
                activeCall = null;
            }
            return;
        }

        Ndef ndef = Ndef.get(tag);
        if (ndef == null) {
            if (activeCall != null) {
                activeCall.reject("NDEF not supported by this tag.");
                activeCall = null;
            }
            return;
        }

        try {
            ndef.connect();
            NdefMessage ndefMessage = ndef.getNdefMessage();
            NdefRecord[] records = ndefMessage.getRecords();
            StringBuilder tagData = new StringBuilder();

            for (NdefRecord record : records) {
                if (record.getTnf() == NdefRecord.TNF_WELL_KNOWN &&
                        Arrays.equals(record.getType(), NdefRecord.RTD_TEXT)) {
                    byte[] payload = record.getPayload();
                    String text = new String(payload, 3, payload.length - 3, Charset.forName("UTF-8"));
                    tagData.append(text).append("\n");
                }
            }

            if (activeCall != null) {
                JSObject result = new JSObject();
                result.put("tagData", tagData.toString().trim());
                activeCall.resolve(result);
                activeCall = null;
            }

        } catch (Exception e) {
            if (activeCall != null) {
                activeCall.reject("Error reading NFC tag: " + e.getMessage());
                activeCall = null;
            }
        } finally {
            try {
                ndef.close();
            } catch (Exception ignored) {}
        }
    }
}