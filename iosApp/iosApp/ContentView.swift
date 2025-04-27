import SwiftUI
import shared

struct ContentView: View {
	let greet = Greeting().greet()

	var body: some View {
        ZStack {
            ComposeViewController()
        }
	}
}

struct ComposeViewController: UIViewControllerRepresentable {
    
    
    
    func makeUIViewController(context: Context) -> UIViewController {
        return MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        
    }
    
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
