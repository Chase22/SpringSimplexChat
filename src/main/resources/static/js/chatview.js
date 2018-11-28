let lastId = -1;
let lastDate = null;

document.addEventListener('DOMContentLoaded', () => {
    console.log("Content Loaded");
    if (document.cookie) {
        document.getElementById("username").value = document.cookie;
    }
    updateMessages();

    const messageBox = document.getElementById("messageBox");
    messageBox.scrollTop = messageBox.scrollHeight;

    document.getElementById('sendMessageForm').addEventListener("submit", (e) => {
        e.preventDefault();
        const formdata = {};

        formdata["username"] = document.getElementById("username").value;
        formdata["message"] = document.getElementById("message").value;
        formdata["chatid"] = document.getElementById("chatid").value;

        document.getElementById("message").value = "";

        document.cookie = formdata["username"];

        formdata["timestamp"] = Date.now();

        fetch("/api/message/send", {
            method: 'post',
            body: JSON.stringify(formdata),
            headers: {'Content-Type': 'application/json'}
        }).then(() => updateMessages);
    });

    setInterval(updateMessages, 10000);
});

const createChatMessage = (value) => {
    const time = new Date(value["timestamp"]);

    return document.createRange().createContextualFragment(
        `<div class="chat-message" id="${value["id"]}">
            <span class="chat-message-timestamp">${time.toLocaleTimeString()}</span> - 
            <span class="chat-message-user">${value["userId"]}</span>: 
            <span class="chat-message-message">${value["message"]}</span>
        </div>`);
};

const updateMessages = () => {
    const chatId = document.getElementById("chatid").value;
    const chatBox = document.getElementById("messageBox");

    fetch(`/api/chat/${chatId}/message?offset=${lastId}`)
        .then((response) => response.json())
        .then(data => {
            data.forEach(value => {
                if (!document.getElementById(value["id"])) {
                    let date = new Date(value["timestamp"]);

                    if (lastDate == null || !isSameDay(date, lastDate)) {
                        const elem = document.createElement("div");
                        elem.classList.add("date-message");
                        elem.id = date.getTime().toString();
                        elem.innerText = date.toLocaleDateString();
                        chatBox.appendChild(elem);

                        lastDate = date;
                    }
                    const chatElemen = createChatMessage(value);
                    chatBox.appendChild(chatElemen);
                    lastId = Math.max(lastId, value["id"]);
                }
            })
        });

    const node = Array.from(chatBox.childNodes)[chatBox.childNodes.length-1];

    console.log(node);

    if(node != null && node.nodeType === Node.ELEMENT_NODE) {
        node.scrollIntoView();
    }

};

const isSameDay = (date1, date2) => {
    return withoutTime(date1).getTime() === withoutTime(date2).getTime();
};

const withoutTime = (date) => {
    date.setHours(0,0,0,0);
    return date;
};