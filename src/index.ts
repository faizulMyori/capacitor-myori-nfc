import { registerPlugin } from '@capacitor/core';

export interface MyoriNFCPlugin {
  isAvailable(): Promise<{ isAvailable: boolean }>;
  readTag(): Promise<{ tagData: string }>;
}

const MyoriNFCPlugin = registerPlugin<MyoriNFCPlugin>('MyoriNFCPlugin');

export default MyoriNFCPlugin;