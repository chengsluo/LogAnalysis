import React from 'react';
import {Layout, Row, Col} from 'antd';
import {View} from '@antv/data-set';
import {Chart, Geom, Axis, Tooltip} from 'bizcharts';
import './index.css'
import axios from "axios/index";

var dv;
var scale;

class Welcome extends React.Component {


    constructor() {
        super();
        axios.get('http://localhost:8080/data/total/')
            .then(res => {
                dv = new View().source(res.data);
                dv.transform({
                    type: 'fold',
                    fields: ['count'],
                    key: 'type',
                    value: 'value',
                });
                scale = {
                    value: {
                        alias: 'The Share Price in Dollars',
                        formatter: function (val) {
                            return val / 10000 + '万条';
                        }
                    },
                    year: {
                        range: [0, 1]
                    }
                };
                this.forceUpdate();
            });
    };

    render() {

        return (
            <Layout style={{minHeight: 400, marginTop: 10}}>
                <Row style={{marginTop: 10}}>
                    <Col offset={8}><h3><span
                        style={{fontWeight: 'bold', color: "#096dd9"}}>数据概览-上海大学2017年103天网络日志数据</span></h3>
                    </Col>
                    <Col span={20}>
                        <Chart data={dv}
                               height={500}
                               padding={'auto'} scale={scale} forceFit>
                            <Tooltip crosshairs/>
                            <Axis/>
                            <Geom type="area" position="date*value" color="type" shape='smooth'/>
                            <Geom type="line" position="date*value" color="type" shape='smooth' size={2}/>
                        </Chart>
                    </Col>
                </Row>
            </Layout>
        )
    }
}

export default Welcome;