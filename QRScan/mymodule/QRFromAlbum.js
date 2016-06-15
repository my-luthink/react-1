/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';

var { NativeModules } = require('react-native');
 var QRFromAlbum = NativeModules.QRFromAlbum;
var QRFromAlbumModule = {
    chooseImage:function(callback: Function):
     void{
        QRFromAlbum.chooseImage(callback);
      },
};
module.exports = QRFromAlbumModule;
