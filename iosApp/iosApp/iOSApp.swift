import SwiftUI

@main
struct iOSApp: App {
	var body: some Scene {
		WindowGroup {
		    ZStack {
		        Color.white.ignoresSafeArea(.all)
			    ContentView()
			}.preferredColorScheme(.light).ignoresSafeArea(.all)
		}
	}
}