import React, {Component} from 'react';

import reactLogo from './images/react.svg';
import playLogo from './images/play.svg';
import javaLogo from './images/java.webp';

import './App.css';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {title: ''};
  }

  async componentDidMount() {
    const response = await fetch('/summary');
    const resContent = await response.json();

    this.setState({
      title: resContent.content
    });
  }

  render() {
    return (
      <div className="App">
        <h1>Welcome to {this.state.title}!</h1>
        <nav>
          <a>
            <img width="400" height="400" src={javaLogo}/>
          </a>
          <a>
            <img width="400" height="400" src={playLogo}/>
          </a>
          <a>
            <img width="400" height="400" src={reactLogo} className="App-logo" alt="logo"/>
          </a>
        </nav>
      </div>
    );
  }
}

export default App;
