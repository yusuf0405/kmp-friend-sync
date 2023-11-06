import androidx.compose.ui.window.ComposeUIViewController
import org.joseph.friendsync.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
