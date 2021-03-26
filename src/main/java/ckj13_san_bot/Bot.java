package ckj13_san_bot;

import java.io.IOException;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

	// получает сообщение от клиента и отправляет ответ
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			// сообщение от клиента
			Message msg = update.getMessage();
			String text = msg.getText();
			System.out.println(text);

			// ответ
			try {
				text = Weather.getWeather(text);
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
			SendMessage answer = new SendMessage();
			answer.setChatId(msg.getChatId().toString());
			answer.setText(text);

			try {
				// отправляю ответ
				execute(answer);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
	}

	// user name
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return "ckj13_san_bot";
	}

	// токен подключения
	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		return "1680711721:AAHmIBF-1RXynGvlL3E3HGkOC1RxOijHmP8";
	}

}
