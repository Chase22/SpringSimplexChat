import moment from 'moment';

export function createChatMessage(value) {
    const time = moment(value.timestamp);

    let divMessage = document.createElement("div");
    let timestamp = createDiv(time.format('LTS'), "chat-message-timestamp");
    let message = document.createElement("div");
    let messageContent = createDiv(value.message, "chat-message-message-content");
    let user = createDiv(value.userId, "chat-message-user");

    divMessage.id = value.id;
    divMessage.classList.add("chat-message");
    divMessage.appendChild(timestamp);

    message.classList.add("chat-message-message");


    message.appendChild(messageContent);
    message.appendChild(user);

    divMessage.append(message);

    return divMessage;
}

export function createDiv(innerText, classname) {
    let span = document.createElement("div");
    span.innerHTML = innerText;
    span.classList.add(classname);
    return span;
}