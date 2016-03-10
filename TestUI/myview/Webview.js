/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';
var React = require('react-native');
 var {
   View,
   requireNativeComponent,
   PropTypes
 } = React;

class ObservableWebView extends React.Component {
  constructor() {
    super();
  }
  render() {
    return <RCTWebView {...this.props} />;
  }
}

ObservableWebView.propTypes = {
  ...View.propTypes,
  url: PropTypes.string,
  html: PropTypes.string,
};

var RCTWebView = requireNativeComponent('RCTWebView', ObservableWebView);
module.exports = ObservableWebView;


