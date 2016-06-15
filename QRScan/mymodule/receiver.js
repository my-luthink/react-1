/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';

var { NativeModules } = require('react-native');
 var ReceiverModule = NativeModules.RecevierModule;
var Receiver = {
     getResult:function(
     msg: string,
     callback: Function)://注意这里是Function类 不是function函数
     void{
      ReceiverModule.getResult(msg,callback);
      },
};
module.exports = Receiver;
