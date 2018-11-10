import React, {PureComponent} from 'react';
import {Layout, Row, Breadcrumb, Icon} from 'antd';
import '../App/index.css'
import ReactEcharts from 'echarts-for-react';
import axios from 'axios';

export default class Total extends PureComponent {
    state = {
        date: [],
        ct1: [],
        ct2: [],
        ct3: [],
        ct4: [],
        ct5: [],
        ct6: [],
        ct0: [],
    };

    constructor() {
        super();
        axios.get('http://localhost:8080/data/findDateAndWayAndCount')
            .then(res => {
                var d = res.data.data;
                this.setState({
                    ct1: d.filter(s => s[1] === 1).map(s => s[2]),
                    ct2: d.filter(s => s[1] === 2).map(s => s[2]),
                    ct3: d.filter(s => s[1] === 3).map(s => s[2]),
                    ct4: d.filter(s => s[1] === 4).map(s => s[2]),
                    ct5: d.filter(s => s[1] === 5).map(s => s[2]),
                    ct6: d.filter(s => s[1] === 6).map(s => s[2]),
                    ct0: d.filter(s => s[1] === 0).map(s => s[2]),
                    date: d.filter(s => s[1] === 0).map(s => s[0])
                });
                console.log(this.state);
            })
    };

    getOption = () => {
        return {
            title: {
                text: '上海大学2017下半年外网访问情况',
                subtext: '只包含半年内103天的数据',
                x: 'center',
                y: 'top',
                left: 'center'
            },
            grid: {
                left: '3%',
                right: '4%',
                containLabel: true
            },
            calculable: true,
            tooltip: {
                trigger: 'axis'
            },
            xAxis: {
                axisLabel: {
                    rotate: 30,
                    interval: 2
                },
                axisLine: {
                    lineStyle: {
                        color: 'red'
                    }
                },
                type: 'category',
                boundaryGap: false,
                data: this.state.date,
            },
            yAxis: {
                type: 'value'
            },
            series: [{
                data: this.state.ct0,
                type: 'line',
                stack: '总量',
                name: "其他"
            }, {
                data: this.state.ct6,
                type: 'line',
                stack: '总量',
                name: "test"
            }, {
                data: this.state.ct5,
                stack: '总量',
                type: 'line',
                name: "shucc"
            }, {
                data: this.state.ct4,
                type: 'line',
                stack: '总量',
                name: "管理员"
            }, {
                data: this.state.ct3,
                type: 'line',
                stack: '总量',
                name: "教师"
            }, {
                data: this.state.ct2,
                type: 'line',
                stack: '总量',
                name: "ctcpppoe"
            }, {
                data: this.state.ct1,
                type: 'line',
                stack: '总量',
                name: "无线"
            }]
        };
    };

    render() {
        return (
            <Layout>
                <Row className="bread-crumb">
                    <Breadcrumb>
                        <Breadcrumb.Item><a href="/"><Icon type="home"/>首页</a></Breadcrumb.Item>
                        <Breadcrumb.Item>流量总计</Breadcrumb.Item>
                    </Breadcrumb>
                </Row>
                <Row style={{margin: 10}}>
                <ReactEcharts
                option={this.getOption()}
                style={{height: '500px', width: '100%'}}
                theme={'light'}/>
                </Row>
            </Layout>
        )
    }
}