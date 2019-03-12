import React, { Component } from 'react';
import './App.css';
import Chat from './components/Chat'

class App extends Component {
  render() {
    return (
      <div className="App">
          <header className="App-header">
          </header>
          <main className="App-Content">
              <Chat />
          </main>
      </div>
    );
  }
}

export default App;
