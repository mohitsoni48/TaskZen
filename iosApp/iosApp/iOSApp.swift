import SwiftUI
import shared

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
