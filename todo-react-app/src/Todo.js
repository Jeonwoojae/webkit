import React, { Component } from "react";
import { Checkbox, IconButton, InputBase, ListItem, ListItemSecondaryAction, ListItemText } from "@material-ui/core";
import DeleteOutlined from "@material-ui/icons/DeleteOutlined"

export default class Todo extends Component {
  constructor(props) {
    super(props);
    this.state = { item: props.item, readOnly:true }; // 매개변수 item의 변수/값을 item에 대입
    this.delete = props.delete;
  }

  deleteEventHandler = ()=>{
    this.delete(this.state.item);
  }
  offReadOnlyMode = () =>{
    console.log("Event!",this.state.readOnly);
    this.setState({readOnly:false},()=>{
        console.log("ReadOnly?",this.state.readOnly);
    });
  }
  enterKeyEventHandler = (e) => {
    if(e.key === "Enter"){
        this.setState({readOnly:true})
    }
  }
  editEventHandler = (e) => {
    const thisItem = this.state.item;
    thisItem.title = e.target.value;
    this.setState({item:thisItem});
  }
  checkboxEventHandler = (e)=>{
    console.log("check box event call");
    const thisItem = this.state.item;
    thisItem.done = thisItem.done ? false : true; // thisItemdone = !thisitem.done
    this.setState({item:thisItem});
  }
  render() {
    const item = this.state.item;
    return (
      <ListItem>
        <Checkbox checked={item.done} />
        <ListItemText>
          <InputBase
            inputProps={{ "aria-label": "naked" }}
            type="text"
            id={item.id}
            name={item.id}
            value={item.title}
            multiline={true}
            fullWidth={true}
            onClick={this.offReadOnlyMode}
            onChange={this.editEventHandler}
            onKeyPress={this.enterKeyEventHandler}
          />
        </ListItemText>

        <ListItemSecondaryAction>
            <IconButton aria-label="Delete" onClick={this.deleteEventHandler}>
                <DeleteOutlined />
            </IconButton>
        </ListItemSecondaryAction>
      </ListItem>
    );
  }
}