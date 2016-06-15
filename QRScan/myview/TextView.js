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
class MyTextView extends React.Component {
  constructor() {
    super();
    console.log("MyTextView constructor");
  }
  render() {
    console.log("MyTextView render");
    return (
    <MySelfTextView {...this.props}>
    </MySelfTextView>);
  }
}

MyTextView.propTypes = {
   ...View.propTypes,
  text: PropTypes.string
};

var MySelfTextView = requireNativeComponent('MyTextView', MyTextView);
module.exports = MyTextView;


