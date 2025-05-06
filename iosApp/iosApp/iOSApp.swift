import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
        DependenciesProviderHelper().doInitKoin()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}