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

 class MyImageView extends React.Component{
  constructor() {
     super();
     console.log("MyImageView constructor");
   }
    render() {
      console.log("MyImageView render");
      return (
      <MySelfImageView {...this.props}>
      </MySelfImageView>);
    }
 }

   MyImageView.propTypes = {
    ...View.propTypes,
      src: PropTypes.string,
      borderRadius: PropTypes.number,
      resizeMode: PropTypes.oneOf(['cover', 'contain', 'stretch']),
    };

var MySelfImageView= requireNativeComponent('MyImageView', MyImageView);
module.exports = MyImageView;