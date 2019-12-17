import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import axios from 'axios';

class App extends Component {

  constructor(props) {
    
    super(props);

    this.state = {
      posts: [],
      name: '',
      age: 0
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleChangeAge = this.handleChangeAge.bind(this);
    this.create = this.create.bind(this);
    this.delete = this.delete.bind(this);

    this.list();
  }

  getUrl() {
    return "http://localhost:8080/api/customers";
  }

  render() {
    return (
      <div className="App">
        <div className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h2>Welcome to React</h2>
        </div>
        <p className="App-intro">
          To get started, edit <code>src/App.js</code> and save to reload.
        </p>
        
        <h1>ReactJS get data Customer from Spring</h1>
        <table>
          <thead>
          <tr>
            <th>Name</th>
            <th>Age</th>
            <th>Action</th>
          </tr>
          </thead>
          <tbody>
          {
            this.state.posts.map(function(post) {
            return (
              <tr key={post.id}>
                <td>{post.name}</td>
                <td>{post.age}</td>
                <td>
                  <button onClick={() => this.delete(post.id)}>Delete</button>
                </td>
              </tr>
              )
  
            }, this 
          )}
          </tbody>
        </table>

        <form onSubmit={this.create}>
        <label>
          Name:
          <input type="text" value={this.state.name} onChange={this.handleChange} />
        </label>
        <label>
          Age:
          <input type="number" value={this.state.age} onChange={this.handleChangeAge} />
        </label>
        <input type="submit" value="Submit" />
      </form>
      </div>
    );
  }

  handleChange(event) {
    this.setState({name: event.target.value});
  }
  handleChangeAge(event) {
    this.setState({age: event.target.value});
  }

  list() {
    axios.get(this.getUrl())
      .then(res => {
        const posts = res.data;
        this.setState({ posts: posts });
      });

    // Redux-saga
    // const obj = {
    //   method: 'POST',
    //   headers: {'Content-Type': 'application/x-www-form-urlencoded',Authorization: 'Basic dGVzdGp3dGNsaWVudGlkOk1hWXprU2pta3pQQzU3TA=='},
    //   data: {username: 'admin.admin',password: 'jwtpass',grant_type: 'password',},
    //   crossDomain: true
    // };
    // const requestURL = 'http://10.0.2.43:8080/accountservice/oauth/token';
    // const users = yield call(request, requestURL, obj);

    // Axios
    // axios.defaults.headers.common['Authorization'] = "Basic dGVzdGp3dGNsaWVudGlkOk1hWXprU2pta3pQQzU3TA==";
    // axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
    // var params = new URLSearchParams();
    // params.append('grant_type', 'password');
    // params.append('username', 'admin.admin');
    // params.append('password', 'jwtpass');
    // axios.post("http://10.0.2.43:8080/accountservice/oauth/token", params)
    //   .then(res => {
    //     console.log(res);
    //   });
  }

  create(event) {
    axios.post(this.getUrl(), {
      "name": this.state.name,
      "age": this.state.age
    }).then(() => {
      this.list();
    });
    event.preventDefault();
  }

  update() {
    axios.put(`http://localhost:8080/api/customers/4785074604081152`, {
      "name": "1111",
      "age": "111"
    });
  }

  delete(id) {
    axios.delete(`${this.getUrl()}/${id}`).then(() => {
      this.list();
    });
  }
}

export default App;
