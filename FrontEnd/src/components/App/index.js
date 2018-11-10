import React from 'react';
import {BrowserRouter as Router, Link} from 'react-router-dom';
import {Route, Switch} from 'react-router'
import Total from "../Total";
import Welcome from "../Welcome";
import moment from 'moment';
import LOGO from './logo.png';
import "./index.css"
import 'moment/locale/zh-cn';
import {Layout, Menu, Icon} from 'antd';
import BaiduSearch from "../BaiduSearch";
import ResearchSearch from "../ResearchSearch";
import PaperSearch from "../PaperSearch";
import ComVisit from "../ComVisit";
import OneDay from "../OneDay";
import OnePie from "../OnePie";
const {Content, Footer, Sider, Header} = Layout;
const SubMenu = Menu.SubMenu;
moment.locale('zh-cn');


class App extends React.Component {
    state = {
        collapsed: false,
        logHeight: 80
    };
    toggle = () => {
        this.setState({
            collapsed: !this.state.collapsed,
            logHeight: this.state.logHeight === 80 ? 30 : 80
        });
    };

    render() {
        return <Router>
            <Layout style={{minHeight: '100vh'}}>
                <Sider style={{background: '#fff'}}
                       trigger={null}
                       collapsible
                       collapsed={this.state.collapsed}
                >
                    <div style={{height: '120px'}}>
                        <img src={LOGO} className="logo" style={{height: this.state.logHeight}} alt={"logo"}/>
                    </div>
                    <Menu style={{background: '#fff'}} mode="inline"
                          defaultSelectedKeys={['1']}
                          defaultOpenKeys={['1']}
                          inlineCollapsed={this.state.collapsed}>
                        <SubMenu
                            key="all"
                            title={<span><Icon type="database"/><span>总体情况</span></span>}
                        >
                            <Menu.Item key="1"><Link to="/total">流量总计</Link></Menu.Item>
                            <Menu.Item key="2"><Link to="/baiduSearch">每日热搜</Link></Menu.Item>
                            <Menu.Item key="8"><Link to="/onePie">流量比例</Link></Menu.Item>
                        </SubMenu>

                        <SubMenu
                            key="academic"
                            title={<span><Icon type="book"/><span>上大学术</span></span>}
                        >
                            <Menu.Item key="4"><Link to="/researchSearch">每日关注</Link></Menu.Item>
                            <Menu.Item key="5"><Link to="/paperSearch">论文排行</Link></Menu.Item>
                        </SubMenu>
                        <SubMenu
                            key="shu"
                            title={<span><Icon type="rocket"/><span>校园生活</span></span>}
                        >
                            <Menu.Item key="6"><Link to="/comVisit">媒体占比</Link></Menu.Item>
                            <Menu.Item key="7"><Link to="/oneDay">上网时长</Link></Menu.Item>
                        </SubMenu>
                    </Menu>
                </Sider>
                <Layout>
                    <Header style={{background: '#00447c', padding: 0}}>
                        <h2 ><Icon
                            className="trigger"
                            type={this.state.collapsed ? 'menu-unfold' : 'menu-fold'}
                            onClick={this.toggle}
                        /><span style={{fontWeight:'bold',color:'white'}}>海量网络日志分析与可视化成果展示</span></h2>
                    </Header>
                    <Content style={{margin: '8px', minHeight: 400}}>
                        <Switch>
                            <Route path="/" exact component={Welcome}/>
                            <Route path="/total" exact component={Total}/>
                            <Route path="/baiduSearch" exact component={BaiduSearch}/>
                            <Route path="/researchSearch" exact component={ResearchSearch}/>
                            <Route path="/comVisit" exact component={ComVisit}/>
                            <Route path="/paperSearch" exact component={PaperSearch}/>
                            <Route path="/oneDay" exact component={OneDay}/>
                            <Route path="/onePie" exact component={OnePie}/>
                        </Switch>
                        <Footer style={{textAlign: 'center'}}>
                            Shanghai University ©2018 Created by ChengsLuo
                        </Footer>
                    </Content>
                </Layout>
            </Layout>
        </Router>
    }
}

export default App;