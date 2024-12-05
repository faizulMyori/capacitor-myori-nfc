export interface MyoriNFCPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
