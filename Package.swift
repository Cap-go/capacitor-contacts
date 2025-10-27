// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapgoCapacitorContacts",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "CapgoCapacitorContacts",
            targets: ["CapacitorContactsPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0")
    ],
    targets: [
        .target(
            name: "CapacitorContactsPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/CapacitorContactsPlugin"),
        .testTarget(
            name: "CapacitorContactsPluginTests",
            dependencies: ["CapacitorContactsPlugin"],
            path: "ios/Tests/CapacitorContactsPluginTests")
    ]
)
