import moment from 'moment';
import linkifyHtml from 'linkifyjs/html';

export function createChatMessage(value) {
    const time = moment(value.timestamp);

    let divMessage = document.createElement("div");
    let timestamp = createDiv(time.format('LTS'), "chat-message-timestamp");
    let message = document.createElement("div");
    let user = createDiv(value.userId, "chat-message-user");

    parseMessage(value.message, message);

    divMessage.id = value.id;
    divMessage.classList.add("chat-message");
    divMessage.appendChild(timestamp);

    message.classList.add("chat-message-message");

    message.appendChild(user);

    divMessage.append(message);

    return divMessage;
}

export function createDiv(innerText, classname) {
    let div = document.createElement("div");
    div.innerHTML = innerText;
    div.classList.add(classname);
    return div;
}

function parseMessage(text, message) {
    let div = document.createElement("div");
    div.classList.add("chat-message-message-content");
    message.appendChild(div);

    if (text.startsWith("{{image}}")) {
        handleImage(text, div);
    } else {
        div.innerHTML = linkifyHtml(text);
    }
}

function handleImage(messageText, div) {
    const id = messageText.replace("{{image}}", "");

    let img = document.createElement("img");
    img.classList.add("chat-message-message-image");

    fetch('/api/image/'+id).then(value => {
        if(value.ok) {
            value.json().then(image => {
                img.src = 'data:image/png;base64,'+image.data;
                div.appendChild(img);
            });
        } else {
            div.appendChild(createDiv("Image not found", "chat-message-message-image-error"));
        }
    });
}