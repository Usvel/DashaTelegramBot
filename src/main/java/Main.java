import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;

public class Main {
    public static void main(String[] args) {

        ApiContextInitializer.init();

        //Set up Http proxy
        DefaultBotOptions botOptions  = ApiContext.getInstance(DefaultBotOptions.class);

        //botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
        botOptions.setProxyHost("localhost");
        botOptions.setProxyPort(9150);

        Bot bot = new Bot(botOptions);

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(bot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
