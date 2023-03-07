import React, { Component } from "react";

export default class EditableLabelFichePFEPhone extends Component {
  
  shouldComponentUpdate(nextProps){
        return nextProps.uTBUPhone !== this.nodeSelect.innerHTML;
    }

    componentDidUpdate() {
            if ( this.props.uTBUPhone !== this.nodeSelect.innerHTML ) {
               this.nodeSelect.innerHTML = this.props.uTBUPhone;
            }
           
        }
  
 emitChange = () => {
        var uTBUPhone = this.nodeSelect.innerHTML;
        if (this.props.onChange && uTBUPhone !== this.lastHtml) {
            this.props.onChange({
                target: {
                    value: uTBUPhone
                }
            });
        }
        this.lastHtml = uTBUPhone;
}

render() {
    return(
      <p ref={(ref) => {this.nodeSelect = ref} }
            onInput={this.emitChange} 
            contentEditable
            dangerouslySetInnerHTML={{__html: this.props.uTBUPhone}}>{this.props.value}</p>
    )
    }
}

