/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';

var { NativeModules } = require('react-native');
var VibrateModule = NativeModules.VibrateModule;
var Vibrate = {
     vibrate:function():
     void{
      VibrateModule.vibrate();
      },
};


module.exports = Vibrate;
