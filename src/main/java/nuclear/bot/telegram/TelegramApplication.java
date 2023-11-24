package nuclear.bot.telegram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;

@SpringBootApplication
public class TelegramApplication {
	@Order()
	public static void main(String[] args) {
		SpringApplication.run(TelegramApplication.class, args);
	}
}
