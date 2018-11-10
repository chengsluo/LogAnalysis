import React, {PureComponent} from 'react';
import {Layout, Row, Breadcrumb, Icon} from 'antd';
import '../App/index.css'
import ReactEcharts from 'echarts-for-react';
import axios from 'axios';

export default class OneDay extends PureComponent {
    state = {
        time: [],
        count: [],
    };

    constructor() {
        super();
        axios.get('http://localhost:8080/data/getOneDay')
            .then(res => {
                var d = res.data.data;
                this.setState({
                    time: d.map(s => s[0]+"-"+s[1]),
                    count: d.map(s=>Math.round(s[2]/103))
                });
                console.log(this.state);
            })
    };

    getOption = () => {
        return {
            title: {
                text: '上海大学师生每天24小时平均上网状况',
                subtext: '来源于103天的上网数据',
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
                    interval: 30
                },
                axisLine: {
                    lineStyle: {
                        color: 'red'
                    }
                },
                type: 'category',
                boundaryGap: false,
                data: this.state.time,
            },
            yAxis: {
                type: 'value'
            },
            series: [{
                data: this.state.count,
                type: 'line',
                name: "分钟平均日志数",
                markLine : {
                    data : [
                        {type : 'average', name : '平均值'}
                    ]
                }
            }]
        };
    };

    render() {
        return (
            <Layout>
                <Row className="bread-crumb">
                    <Breadcrumb>
                        <Breadcrumb.Item><a href="/"><Icon type="home"/>首页</a></Breadcrumb.Item>
                        <Breadcrumb.Item>24小时平均上网情况</Breadcrumb.Item>
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