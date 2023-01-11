import React, {useState} from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import authAPI from "./api/auth";
import shotAPI from "./api/api";

function Header(){
    return(
        <div className="header">Ponomarev Vadim. var 1234</div>
    )
}

class LogInputs extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            loginDisabled: true
        }
        this.activateDeActivate = this.activateDeActivate.bind(this)
        this.loginRequest = this.loginRequest.bind(this)
        this.registerRequest = this.registerRequest.bind(this)
        this.testFunc = this.testFunc.bind(this);

    }

    testFunc(){
        this.props.updLogState()
    }

    loginRequest(){
        let log = document.getElementById("inputLoginField").value;
        let pass = document.getElementById("inputPasswordField").value;
        // console.log(`Sending ${log} and ${pass}`)
        authAPI.login(log, pass).then(response => {
            if (response.status === 200) {
                localStorage.setItem("lab_username", response.data.username)
                localStorage.setItem("lab_token", response.data.jwt)
                this.testFunc()
            } else {
                console.log("Idi v pizdu");
            }
        }).catch(error => {
            alert(error.response.data)
        });
    }

    registerRequest(){
        let log = document.getElementById("inputLoginField").value;
        let pass = document.getElementById("inputPasswordField").value;
        authAPI.register(log, pass).then(response => {
            if (response.status === 200) {
                alert("You are registered")
            } else {
                alert("Something is going bla bla bla")
            }
        }).catch(error => {
            alert(`WTF ${error}`)
        });
    }


    activateDeActivate(){
        if(document.getElementById("inputLoginField").value.length > 0 && document.getElementById("inputPasswordField").value.length > 0){
            this.setState({
                loginDisabled: false,
            });
        }else{
            this.setState({
                loginDisabled: true,
            });
        }
    }

    render() {
        return(
            <div className="login">
                <input id={"inputLoginField"} type={"text"} size={30} onChange={this.activateDeActivate}/>
                <input id={"inputPasswordField"} type={"password"} size={30} onChange={this.activateDeActivate}/><br/>
                <input id={"loginButton"} type={"button"} value={"Login"} onClick={this.loginRequest} disabled={this.state.loginDisabled}/>
                <input id={"registerButton"} type={"button"} value={"Register"} onClick={this.registerRequest} disabled={this.state.loginDisabled}/>
            </div>
        )
    }
}

class Cell extends React.Component {
    constructor(props){
        super(props);
    }
    render() {
        return(
            <td>{this.props.value}</td>
        )
    }

}

class Row extends React.Component {
    constructor(props){
        super(props);
    }

    generateCell(i){
        return(
            <Cell value={this.props.rowValue[i]} />
        )
    }



    render() {
        return (
            <tr>
                {this.generateCell(0)}
                {this.generateCell(1)}
                {this.generateCell(2)}
                {this.generateCell(3)}
                {this.generateCell(4)}
            </tr>
        )
    }
}


class Table extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            rows: [],
        }
    }


    componentDidMount() {
        shotAPI.getEntries(localStorage.getItem("lab_token")).then(res => {
            if(res.status === 200){
                let rows = []
                for (let i = 0; i < res.data.length; i++) {
                    rows.push([res.data[i].x, res.data[i].y, res.data[i].r, res.data[i].result.toString(), res.data[i].time.replace('T', ' ')])
                }
                this.setState({rows: rows.reverse()})
            }
        })
    }




    generateRow(obj){
        return (
            <Row key={obj} rowValue={obj}/>
        )
    }

    render() {
        console.log(this.state.rows)
        return(
            <div>
                <table>
                    <tbody>
                        {this.generateRow(["X", "Y", "R", "Hit", "Time"])}
                        {this.state.rows.map(x => this.generateRow(x))}
                    </tbody>
                </table>
            </div>
        )
    }

}

class XYRinputs extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            buttDisabled: false,
            x: false,
            y: false,
            r: false,
        }
        this.inputChanged = this.inputChanged.bind(this)
        this.clicked = this.clicked.bind(this)
    }


    inputChanged(){
        let value = document.getElementById("input-y").value
        if(isNaN(parseFloat(value))){
            this.setState({buttDisabled: true})
        }else{
            let float = parseFloat(value);
            if( -5 > float || float > 5){
                this.setState({buttDisabled: true})

            }else{
                this.setState({buttDisabled: false})
            }
        }
    }

    componentDidMount() {
        if(this.state.x && this.state.y && this.state.r){

        }
    }

    wrap = () => {
        this.clicked()
        this.forceUpdate()
    }


    clicked = (e) => {
        let x = document.getElementById("select-x").options[document.getElementById("select-x").selectedIndex].text;
        let y = document.getElementById("input-y").value;
        let r = document.getElementById("select-r").options[document.getElementById("select-r").selectedIndex].text;
        shotAPI.sendShot(x, y, r, localStorage.getItem("lab_token")).then(response => {
            if(response.status === 200){
                console.log("200 lulz")
            }else{
                console.log("Not 200")
            }
        })

    }

    render() {
        return (
            <div>
                <span>X</span>
                <select id={"select-x"}>
                    <option className={"fontC"}>-2</option>
                    <option className={"fontC"}>-1.5</option>
                    <option className={"fontC"}>-1</option>
                    <option>-0.5</option>
                    <option selected={"selected"}>0</option>
                    <option>0.5</option>
                    <option>1</option>
                    <option>1.5</option>
                    <option>2</option>
                </select><br/>
                <span>Y</span>
                <input id={"input-y"} type={"text"} size={30} onChange={this.inputChanged} defaultValue={'0'}/><br/>
                <span>R</span>
                <select id={"select-r"}>
                    <option className={"fontC"}>-2</option>
                    <option className={"fontC"}>-1.5</option>
                    <option className={"fontC"}>-1</option>
                    <option>-0.5</option>
                    <option>0</option>
                    <option>0.5</option>
                    <option selected={"selected"}>1</option>
                    <option>1.5</option>
                    <option>2</option>
                </select><br/>
                <input id={"send-button"} type={"button"} onClick={() => {this.clicked(); this.setState({kosts: 3})}} value={"Send"} disabled={this.state.buttDisabled}/>
                <Table />
            </div>
        )
    }
}


class ShitPage extends React.Component {
    constructor(props){
        super(props);
        this.testFunc = this.testFunc.bind(this);
        this.state = {
            loading: false,
        }
    }

    handleOnUpload = () => {
        this.setState({
            loading: true,
        });
    }

    handleOnUploadEnd = () => {
        this.setState({
            loading: false,
        });
    }

    testFunc(){
        localStorage.setItem("lab_username", null)
        localStorage.setItem("lab_token", "fake_token_lallala")
        // this.props.updLogState()
        this.props.setLogState(false)
    }

    render() {
        return(
            <div>
                {/*<div>Poshel otsuda nahui</div>*/}
                <input type={"button"} value={"Poiti naher"} onClick={this.testFunc}/><br/>
                <img src={require('./areas.png')}/><br/>
                <XYRinputs onUpload={this.handleOnUpload} onUploadEnd={this.handleOnUploadEnd} />
            </div>
        )
    }
}

class WelcomePage extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {
        return(
            <div className={"trio"}>
                <div><Header /></div>
                <div className="clock"><Clock /></div>
                <LogInputs updLogState={this.props.updLogState}/>
            </div>
        )
    }
}


class Clock extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            time: new Date().toLocaleTimeString()
        }
    }
    componentDidMount() {
        this.intervalID = setInterval(() => this.updateClock(),1000);
    }

    componentWillUnmount(){
        clearInterval(this.intervalID)
    }
    updateClock(){
        let now = new Date();
        let timeString = [now.getHours(), now.getMinutes(), now.getSeconds() < 10 ? ( "0" + now.getSeconds()) : now.getSeconds()].join(':');
        let dateString = [now.getDate() < 10 ? ( "0" + now.getDate()) : now.getDate(), now.getMonth() + 1, now.getFullYear()].join('.');
        let date = [dateString, timeString].join(' | ');
        this.setState({
            time: date
        });

    }
    render() {
        return (
            <div className="Time">
                <p> {this.state.time}</p>
            </div>
        );}
}


class Application extends React.Component{
    constructor(props) {
        super(props);
        let state = false;

        let token = localStorage.getItem("lab_token")
        if(token == null || token === "fake_token_lallala"){
            state = false
        }else{
            state = true
        }

        this.state = {
            loggedIn: state,
        }
        this.updateLoginState = this.updateLoginState.bind(this)
        this.setLogState = this.setLogState.bind(this)
    }

    setLogState(value){
        this.setState({
            loggedIn: value,
        })
    }

    updateLoginState(){
        this.setState({
            loggedIn: !this.state.loggedIn,
        })
    }



    render() {
        return(
            !this.state.loggedIn ? <WelcomePage updLogState={this.updateLoginState} /> : <ShitPage updLogState={this.updateLoginState} setLogState = {this.setLogState}/>
        )
    }
}

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(<Application />);
