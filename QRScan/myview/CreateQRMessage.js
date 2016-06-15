/**
 *TextView.js
 */
'use strict';
 var React = require('react-native');

 var {
   View,
   StyleSheet,
   Text,
   Image,
   TextInput,
   TouchableHighlight,
   requireNativeComponent,
   PropTypes,
   TextView,
   ToastAndroid,
   NativeModules
 } = React;

 var CreateQRImage = NativeModules.CreateQRImage;//生成二维码图片

 class MyCreateQRView extends React.Component {

  constructor() {
    super();
    this.state= {
     name:'',
     content:'',
    };
    console.log("MyCreateQRView constructor");
  }

//  propTypes: {
//      flag:React.PropTypes.bool,
//    }

    onClick(name,content){
    if(name==null||content==null||name==''||content==''){
      ToastAndroid.show("标题或内容不能为空",ToastAndroid.SHORT);
      return;
    }
         CreateQRImage.createImage(name,content);
           this.setState({
                        name:'',
                        content:'',
                      });
     }

  render() {
    console.log("MyCreateQRView render");
      return (
         <View style={styles.container}>
         <View style={styles.border}>
           <TextInput
             style={styles.title}
             autoFocus={true}
             maxLength={20}
             underlineColorAndroid={'transparent'}
             placeholder="标题 不能为空"
             onChangeText={(name) => this.setState(
             {name:name,content:this.state.content})}
             value={this.state.name}>
            </TextInput>
          </View>

           <TextInput
             placeholder="内容 不能为空"
             multiline={true}
             style={styles.content}
             underlineColorAndroid={'transparent'}
             onChangeText={(content) => this.setState(
             {name:this.state.name,content:content})}
             value={this.state.content}>
           </TextInput>
           <View style={styles.border}></View>
           <View style={styles.containerButton}>
             <TouchableHighlight
             underlayColor='#4169e1'
             style={styles.button}
             onPress={()=>{this.onClick(this.state.name,this.state.content);}}>
               <Text style={{flex:1,fontSize: 18,alignSelf:'center',}}>确定</Text>
             </TouchableHighlight>
           </View>
         </View>);
  }
}

var styles = StyleSheet.create({
container:{//最外层
  flex:1,
 },
title:{
  padding: 4,
  height:50,
  fontSize: 16,
  textAlignVertical:'center',
  marginTop:10,
 },
content:{
  flex:1,
  fontSize: 16,
  padding: 4,
  textAlignVertical:'top',
 },
 border:{
  borderColor: 'gray',
  borderWidth: 1,
 },
 containerButton:{
  justifyContent: 'center',
  alignItems: 'center',
 },
button:{
  backgroundColor:'#dcdcdc',
  width:100,
  height:40,
  alignItems: 'center',
  justifyContent:'center',
  margin:5,
 },
});

MyCreateQRView.propTypes = {
   ...View.propTypes,
};

module.exports = MyCreateQRView;


