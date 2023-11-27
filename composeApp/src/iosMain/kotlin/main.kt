import androidx.compose.ui.window.ComposeUIViewController
import org.joseph.friendsync.app.App
import org.joseph.friendsync.di.appModules
import org.joseph.friendsync.di.getSharedModule
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }

fun initKoin() {
    val koinApp = startKoin {
        modules(appModules() + getSharedModule())
    }.koin
}
