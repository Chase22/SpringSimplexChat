export class Chatconector {

    constructor(chatId) {
        this.chatId = chatId;
    }

    sendMessage(username, message) {
        const formdata = {};

        formdata["username"] = username;
        formdata["message"] = message;
        formdata["chatid"] = this.chatId;
        formdata["timestamp"] = Date.now();

        console.log("Sending message");

        return fetch('/api/message/send', {
            method: 'POST',
            body: JSON.stringify(formdata),
            headers: {'Content-Type': 'application/json'}
        });
    }

    getUpgrades(offset) {
        return fetch(`/api/chat/${this.chatId}/message?offset=${offset}`)
            .then((response) => response.json());
    }
}
