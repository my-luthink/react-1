/**
 *TextView.js
 */
'use strict';
var React = require('react-native');
 var {
   View,
   requireNativeComponent,
   PropTypes
 } = React;
class MyReactQRView extends React.Component {
  constructor() {
    super();
    console.log("MyReactQRView constructor");
  }
  render() {
    console.log("MyReactQRView render");
    return (
    <MySelfQRView {...this.props}>
    </MySelfQRView>);
  }
}

MyReactQRView.propTypes = {
   ...View.propTypes,
};

var MySelfQRView = requireNativeComponent('ReactQRView', MyReactQRView);
module.exports = MyReactQRView;


