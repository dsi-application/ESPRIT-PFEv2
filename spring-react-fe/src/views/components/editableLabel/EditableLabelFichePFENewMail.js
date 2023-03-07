import React, { Component } from "react";

export default class EditableLabelFichePFENewMail extends Component {
  
  shouldComponentUpdate(nextProps){
        return nextProps.uTBUNewMail !== this.nodeSelect.innerHTML;
    }

    componentDidUpdate() {
            if ( this.props.uTBUNewMail !== this.nodeSelect.innerHTML ) {
               this.nodeSelect.innerHTML = this.props.uTBUNewMail;
            }
           
        }
  
 emitChange = () => {
        var uTBUNewMail = this.nodeSelect.innerHTML;
        if (this.props.onChange && uTBUNewMail !== this.lastHtml) {
            this.props.onChange({
                target: {
                    value: uTBUNewMail
                }
            });
        }
        this.lastHtml = uTBUNewMail;
}

render() {
    return(
      <p ref={(ref) => {this.nodeSelect = ref} }
            onInput={this.emitChange} 
            contentEditable
            dangerouslySetInnerHTML={{__html: this.props.uTBUNewMail}}>{this.props.value}</p>
    )
    }
}

