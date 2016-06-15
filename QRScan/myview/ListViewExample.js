/**
 * The examples provided by Facebook are for non-commercial testing and
 * evaluation purposes only.
 *
 * Facebook reserves all rights not expressly granted.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL
 * FACEBOOK BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN
 * AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @flow
 */
'use strict';

var React = require('react-native');
var {
  Image,
  ListView,
  TouchableHighlight,
  StyleSheet,
  Text,
  View,
  ToastAndroid,
  NativeModules,
} = React;

//var UIExplorerPage = require('./myview/UIExplorerPage');
var path = [];
var content = [];
var name = [];
var SearchQRImage = NativeModules.SearchQRImage;
var ListViewSimpleExample = React.createClass({
  statics: {
    title: 'ListView',
    description: 'Performant, scrollable list of data.'
  },

  getInitialState: function() {
    //this._getData();
    var ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
    return {
//      dataSource: ds.cloneWithRows(this._genRows({})),
        dataSource: ds.cloneWithRows(this._getData({})),
    };
  },

  _pressData: ({}: {[key: number]: boolean}),

  componentWillMount: function() {
    this._pressData = {};
  },

  render: function() {
    return (
     <ListView
              dataSource={this.state.dataSource}
              renderRow={this._renderRow}
            />
//      <UIExplorerPage
//        title={this.props.navigator ? null : '<ListView> - Simple'}
//        noSpacer={true}
//        noScroll={true}>
//
//      </UIExplorerPage>
    );
  },

  _renderRow: function(rowData: string, sectionID: number, rowID: number) {
//    var rowHash = Math.abs(hashCode(rowData));
// onPress={() => this._pressRow(rowID)}
    var imgSource = {
      uri: "file://"+path[0],
    };
    return (
      <TouchableHighlight>
        <View>
          <View style={styles.row}>
            <Image style={styles.thumb} source={imgSource} />
            <Text style={styles.text}>
              {rowData + ' - ' + name[rowID]}
            </Text>
          </View>
          <View style={styles.separator} />
        </View>
      </TouchableHighlight>
    );
  },

  _genRows: function(pressData: {[key: number]: boolean}): Array<string> {
    var dataBlob = [];
    for (var ii = 0; ii < name.length; ii++) {
      //var pressedText = pressData[ii] ? ' (pressed)' : ''; '名称：' + name[ii] +"; 内容: " + content[ii] +"; 路径: "+path[ii]
      dataBlob.push('nihoa');
    }
    return dataBlob;
  },

   _getData:function(){//从android端获取数据
     var arr = [];
    SearchQRImage.searchImage((res)=>{
      arr = JSON.parse(res);
     for(var data in arr){//data 是arr的下标
        path.push(data.path);
        content.push(data.content);
        name.push(data.name);
        console.log(arr[data].name);
        console.log(arr[data].path);
        console.log(arr[data].content);
        console.log(res);
        console.log(data);
      }
    });
    return arr;
  },

  _pressRow: function(rowID: number) {
    this._pressData[rowID] = !this._pressData[rowID];
    this.setState({dataSource: this.state.dataSource.cloneWithRows(
      this._genRows(this._pressData)
    )});
  },
});


//var THUMB_URLS = [
//  'image/like.png',
//  'image/dislike.png',
//  'image/call.png',
//  'image/fist.png',
//  'image/bandaged.png',
//  'image/flowers.png',
//  'image/heart.png',
//  'image/liking.png',
//  'image/party.png',
//  'image/poke.png',
//  'image/superlike.png',
//  'image/victory.png',
//  ];
var LOREM_IPSUM = '名称 内容 路径';
/* eslint no-bitwise: 0 */
//var hashCode = function(str) {
//  var hash = 15;
//  for (var ii = str.length - 1; ii >= 0; ii--) {
//    hash = ((hash << 5) - hash) + str.charCodeAt(ii);
//  }
//  return hash;
//};

var styles = StyleSheet.create({
  row: {
    flexDirection: 'row',
    justifyContent: 'center',
    padding: 10,
    backgroundColor: '#F6F6F6',
  },
  separator: {
    height: 1,
    backgroundColor: '#CCCCCC',
  },
  thumb: {
    width: 150,
    height: 150,
  },
  text: {
    flex: 1,
  },
});

module.exports = ListViewSimpleExample;
