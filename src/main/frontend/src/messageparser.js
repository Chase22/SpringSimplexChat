export function createChatMessage(value) {
    const time = new Date(value.timestamp);

    let divMessage = document.createElement("div");
    divMessage.id = value.id;
    divMessage.classList.add("chat-message");

    let spanTimestamp = createSpan(time.toLocaleTimeString(), "chat-message-timestamp");
    let spanUser = createSpan(value.userId, "chat-message-user");
    let spanMessage = createSpan(value.message, "chat-message-message");

    divMessage.appendChild(spanTimestamp);
    divMessage.appendChild(spanUser);
    divMessage.appendChild(spanMessage);

    return divMessage;
}

export function createSpan(innerText, classname) {
    let span = document.createElement("span");
    span.innerText = innerText;
    span.classList.add(classname);
    return span;
}