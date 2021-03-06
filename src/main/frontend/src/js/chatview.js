import {Chatconector} from './chatconector';
import {createChatMessage} from "./messageparser";
import moment from 'moment';

import {uppy, uppyInstance} from "./uppy";

const connector = new Chatconector(window.chatId);

const usernameField = document.getElementById("username");
const messageField = document.getElementById("message");

let lastId = -1;
let lastDate = null;

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('submit').addEventListener("click", (e) => {
        e.preventDefault();
        connector.sendMessage(usernameField.value, messageField.value).then(() => updateMessages);
        uppyInstance.reset();
    });

    setInterval(updateMessages, 10000);

    updateMessages();
    uppy(messageField);
});

const updateMessages = () => {
    const chatBox = document.getElementById("messageBox");

    connector.getUpdates(lastId)
        .then(data => {
            data.forEach(value => {
                if (!document.getElementById(value.id)) {
                    let date = moment(value.timestamp);

                    if (lastDate == null || !date.isSame(lastDate, 'day')) {
                        const elem = document.createElement("div");
                        elem.classList.add("date-message");
                        elem.id = date.unix().toString();
                        elem.innerText = date.format("L");
                        chatBox.appendChild(elem);
                        console.log("Append child: " + elem);

                        lastDate = date;
                    }
                    const chatElemen = createChatMessage(value);
                    chatBox.appendChild(chatElemen);
                    lastId = Math.max(lastId, value["id"]);
                }
            })
        });

    const node = Array.from(chatBox.childNodes)[chatBox.childNodes.length-1];

    if(node != null && node.nodeType === Node.ELEMENT_NODE) {
        // node.scrollIntoView();
    }
};
