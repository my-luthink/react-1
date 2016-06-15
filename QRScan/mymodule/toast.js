/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';

var { NativeModules } = require('react-native');
 var ToastM = NativeModules.ToastM;
var Toast = {
     SHORT:ToastM.SHORT,
     LONG:ToastM.LONG,
     show:function(
     msg: string,
     duration: number):
     void{
      ToastM.show(msg,duration);
      },
};


module.exports = Toast;
