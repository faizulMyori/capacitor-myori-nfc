// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "MyoriNfc",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "MyoriNfc",
            targets: ["MyoriNFCPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "MyoriNFCPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/MyoriNFCPlugin"),
        .testTarget(
            name: "MyoriNFCPluginTests",
            dependencies: ["MyoriNFCPlugin"],
            path: "ios/Tests/MyoriNFCPluginTests")
    ]
)