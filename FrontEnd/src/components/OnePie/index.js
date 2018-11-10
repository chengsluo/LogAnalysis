import React, {PureComponent} from 'react';
import {Layout, Row, Breadcrumb, Icon} from 'antd';
import '../App/index.css'
import ReactEcharts from 'echarts-for-react';
import axios from 'axios';

export default class OnePie extends PureComponent {
    state = {
        data: [],
    };

    constructor() {
        super();
        axios.get('http://localhost:8080/data/getPie')
            .then(res => {
                var d = res.data.data;
                this.setState({
                    data:d
                });
                console.log(this.state);
            })
    };

    getOption = () => {
        return {
            title: {
                text: '互联网前20企业占比',
                subtext: '整个饼图仅表示1/7的互联网',
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
                trigger: 'item',
                formatter: "{a} <br/>{b}: {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                x: 'left',
                data:this.state.data.map(s=>s[0])
            },
            series: [
                {
                    name:'域名',
                    type:'pie',
                    radius: ['50%', '80%'],
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            show: true,
                            textStyle: {
                                fontSize: '30',
                                fontWeight: 'bold'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    data:this.state.data
                }
            ]
        };
    };

    render() {
        return (
            <Layout>
                <Row className="bread-crumb">
                    <Breadcrumb>
                        <Breadcrumb.Item><a href="/"><Icon type="home"/>首页</a></Breadcrumb.Item>
                        <Breadcrumb.Item>流量平均走向</Breadcrumb.Item>
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