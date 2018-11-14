
document.addEventListener('DOMContentLoaded', () => {
    console.log("Content Loaded");
    if (document.cookie) {
        document.getElementById("username").value = document.cookie;
    }
    updateMessages();

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

    setInterval(updateMessages, 1000);
});

const createChatMessage = (value) => {
    const time = new Date(value["timestamp"]);

    return document.createRange().createContextualFragment(
        `<div class="chat-message" id="${value["id"]}">
            <span class="chat-message-timestamp">${time.toLocaleString()}</span> - 
            <span class="chat-message-user">${value["userId"]}</span>: 
            <span class="chat-message-message">${value["message"]}</span>
        </div>`);
};

const updateMessages = () => {
    const chatId = document.getElementById("chatid").value;
    const chatBox = document.getElementById("messageBox");

    fetch(`/api/chat/${chatId}/message`)
        .then((response) => response.json())
        .then(data => {
            data.forEach(value => {
                if (!document.getElementById(value["id"])) {
                    chatBox.appendChild(createChatMessage(value))
                }
            })
        });
};