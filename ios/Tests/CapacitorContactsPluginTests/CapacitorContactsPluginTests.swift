import XCTest
@testable import CapacitorContactsPlugin

final class CapacitorContactsPluginTests: XCTestCase {
    func testPluginInitialises() {
        let plugin = CapacitorContactsPlugin()
        XCTAssertEqual(plugin.identifier, "CapacitorContactsPlugin")
    }
}
