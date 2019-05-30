import React from 'react'

class NoMatch extends React.Component {

    render() {
    return (
        <div>
          <h3>
Page Not Found
          </h3>
          <h3>
            <button onClick={this.goBack.bind(this)} class="btn btn-warning"><span class="glyphicon glyphicon-home"></span>
            Try to go home </button>
          </h3>
        </div>
      );
    }
    
      goBack() {
        this.props.history.goBack()
      }
}

export default NoMatch;