import React from 'react'

class AccesDenied extends React.Component {

    render() {
        return (
            <div className="container">
                <div className="row">
                    <div className="col-md-12">
                        <div className="error-template">

                            <h2>
                                403 Acces Denied</h2>
                            <div className="error-actions">
                                <button onClick={this.goBack.bind(this)} class="btn btn-warning"><span class="glyphicon glyphicon-home"></span>
                                    Go Back </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
    goBack() {
        this.props.history.push("/user")
    }
}

export default AccesDenied;