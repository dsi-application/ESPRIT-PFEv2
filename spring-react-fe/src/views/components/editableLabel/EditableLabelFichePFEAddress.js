import React, { Component } from "react";


export default class EditableLabelFichePFEAddress extends Component {
  
  shouldComponentUpdate(nextProps){
        return nextProps.uTBUAddress !== this.nodeSelect.innerHTML;
    }

    componentDidUpdate() {
            if ( this.props.uTBUAddress !== this.nodeSelect.innerHTML ) {
               this.nodeSelect.innerHTML = this.props.uTBUAddress;
            }
           
        }
  
 emitChange = () => {
        var uTBUAddress = this.nodeSelect.innerHTML;
        if (this.props.onChange && uTBUAddress !== this.lastHtml) {
            this.props.onChange({
                target: {
                    value: uTBUAddress
                }
            });
        }
        this.lastHtml = uTBUAddress;
}

render() {
    return(
      <p ref={(ref) => {this.nodeSelect = ref} }
            onInput={this.emitChange} 
            contentEditable
            dangerouslySetInnerHTML={{__html: this.props.uTBUAddress}}>{this.props.value}</p>
    )
    }
}

