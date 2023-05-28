# Hiki List

![logo](https://i.imgur.com/C1AAS6d.png)

Это очередной мобильный клиент для шикимори.

---

[![Button Icon](https://img.shields.io/badge/Trello-0052CC?style=for-the-badge&logo=Trello)](https://trello.com/b/QbSfiAtq/hikilist)


## Скачать 😋

Если я не поленился и сделал сделал релиз, то его можно скачать [тута](https://github.com/ilfey/Hiki-List/releases/latest). В противном случае см пунт ниже. 😎👍

## Сборка приложения 🧑‍🔧

1. Клонируете или качаете zip
2. Запускаете проектик в студии
3. Регистрируете аккаунт на [shikimori](https://shikimori.one), если не зареганы
4. Регистрируете oauth приложение вот [тут](https://shikimori.one/oauth/applications). Если не хотите регать приложение, то могу посоветовать покапаться в сорцах других клиентов или заглянуть на форум 😄
5. Копируете Client Id и Client Secret. Желательно эти данные не палить. Многим пофиг, а мне нет. Поэтому ручками создавайте себе связку
6. Настраиваем файлик `local.properties` (студия его сама гинерит) туды после `sdk.dir=...` надо вставить строки следущего форматика (A4):


```properties
shikimori.app_url=<ссылка на сайт, на случай если домен поменяется 🙂>
shikimori.app_name=<название-вашего-приложения>
shikimori.client_id=<client-id>
shikimori.client_secret=<client-secret>
```
7. Нажимаем кнопочку ***build***
8. Радуетесь жизни 🥳🥳🥳
