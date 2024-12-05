import Foundation

@objc public class MyoriNFC: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
