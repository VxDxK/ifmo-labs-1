package core.guiclient.gui.locales;

import java.util.ListResourceBundle;

public class loc_ru extends ListResourceBundle {
    private final String[][] translates = {
            {"conn", "Подключиться"},
            {"esa", "Введите адрес сервера"},
            {"inp", "Вводит билета"},
            {"fine_inp", "Поля введены верно"},
            {"loaded", "Загружено: %d команд, с сервера по адресу: %s"},
            {"err", "Ошибка"},
            {"exec", "Выполнить"},
            {"mode_exec", "Выполнение команд"},
            {"mode_table", "Таблице билетов"},
            {"mode_plane", "Плоскость билетов"},
            {"logged_as", "Вы работаете под пользователем: "},
            {"fine_startup", "Запуск"},
            {"fl", "Выполнен вход"},
            {"serv_addr_down", "Сервер по адресу: %s недоступен"},
            {"server_down", "Сервер лежит"},
            {"loq_req", "Необходимо войти"},
            {"no_element", "Не был задан билет"},
            {"remove", "Удалить"},
            {"upd", "Обновить билет"},
            {"in", "Войти"},
            {"up", "Зарегистрироваться"},
            {"out", "Выйти"},
            {"not_log", "Вы не вошли в свой аккаунт"},
            {"lr", "Нужно войти в аккаунт"}

    };
    @Override
    protected String[][] getContents() {
        return translates;
    }
}
