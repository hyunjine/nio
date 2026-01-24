import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.android.tools.screenshot.PreviewTest
import com.hyunjine.common.ui.theme.NioTheme

@PreviewTest
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Text(text = "hi")
}