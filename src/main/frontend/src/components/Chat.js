import React, {Component} from 'react';
import Message from './Message'


class Chat extends Component {
    constructor() {
        super();

        this.state = {messages: []}
    }

    render() {
        return this.state.messages.map((message) => <Message message={message}/>);
    }

    componentDidMount() {
        console.log("component did mount");
        fetch("/api/chat/ff8081816713622e0167136232470000/message", {
            mode: 'no-cors'
        })
            .then(response => {
                return response.json();
            }).then(json => {
                this.setState({messages: json.messages})
            }).catch(reason => console.log(reason))
    }
}

export default Chat;