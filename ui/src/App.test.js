import React from 'react';
import ReactDOM from 'react-dom';
import Enzyme from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import renderer from 'react-test-renderer';

import App from './App';
import Client from "./Client";

Enzyme.configure({ adapter: new Adapter() });

jest.mock('../src/Client');

describe('App tests', () => {
  let app;
  const response = {
    content: 'Java Play React Seed'
  };

  beforeEach(() => {
    app = Enzyme.shallow(
      <App/>
    );
  });

  afterEach(() => {
    Client.getSummary.mockClear();
  });

  it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<App />, div);
    ReactDOM.unmountComponentAtNode(div);
  });

  it('should set the state property `title`', () => {
    const invocationArgs = Client.getSummary.mock.calls[0];
    const cb = invocationArgs[0];
    cb(response);
    app.update();

    expect(
      app.state().title
    ).toEqual(response.content);
  });
});
