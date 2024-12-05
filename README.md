# myori-nfc

NFC Plugin for MyORI SmartSecure

## Install

```bash
npm install myori-nfc
npx cap sync
```

## API

<docgen-index>

* [`isAvailable()`](#isavailable)
* [`readTag()`](#readtag)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### isAvailable()

```typescript
isAvailable() => Promise<{ isAvailable: boolean; }>
```

**Returns:** <code>Promise&lt;{ isAvailable: boolean; }&gt;</code>

--------------------


### readTag()

```typescript
readTag() => Promise<{ tagData: string; }>
```

**Returns:** <code>Promise&lt;{ tagData: string; }&gt;</code>

--------------------

</docgen-api>
