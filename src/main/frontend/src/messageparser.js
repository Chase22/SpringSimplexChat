import moment from 'moment';

export function createChatMessage(value) {
    const time = moment(value.timestamp);

    let divMessage = document.createElement("div");
    divMessage.id = value.id;
    divMessage.classList.add("chat-message");

    let timestamp = createDiv(time.format('LTS'), "chat-message-timestamp");
    let user = createDiv(value.userId, "chat-message-user");
    let message = createDiv(value.message, "chat-message-message");

    divMessage.appendChild(timestamp);
    divMessage.appendChild(message);
    divMessage.appendChild(user);

    return divMessage;
}

export function createDiv(innerText, classname) {
    let span = document.createElement("div");
    span.innerText = innerText;
    span.classList.add(classname);
    return span;
}