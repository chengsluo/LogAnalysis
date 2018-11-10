import React, {PureComponent} from 'react';
import {Layout, Row, Col, Breadcrumb, Icon, DatePicker} from 'antd';
import '../App/index.css'
import axios from "axios/index";
import WordCloud from 'react-d3-cloud';
import moment from 'moment';

const dateFormat = 'YYYY-MM-DD';
const fontSizeMapper = word => Math.log2(word.value) * 20;

export default class PaperSearch extends PureComponent {
    state = {
        wc: [],
        date: "2017-07-14",
    };

    componentDidMount() {
        axios.get('http://localhost:8080/data/paperWordCount/' + this.state.date)
            .then(res => {
                this.setState({wc: res.data.data});
            });
    }

    onDatePickerChange = (date, dateString) => {
        axios.get('http://localhost:8080/data/paperWordCount/' + dateString)
            .then(res => {
                this.setState({wc: res.data.data});
            });
    };

    render() {
        return (
            <Layout>
                <Row className="bread-crumb">
                    <Breadcrumb>
                        <Breadcrumb.Item><a href="/"><Icon type="home"/>首页</a></Breadcrumb.Item>
                        <Breadcrumb.Item>百度论文</Breadcrumb.Item>
                    </Breadcrumb>
                </Row>
                <Row style={{marginTop: 10}}>
                    <Col offset={20} span={4}>
                        <DatePicker
                            format="YYYY-MM-DD"
                            defaultValue={moment('2017-07-14', dateFormat)}
                            onChange={this.onDatePickerChange}
                        />
                    </Col>
                    <Col span={24}>
                        <h2 align="center">师生关注的论文排行榜</h2>
                        <WordCloud
                            height={500}
                            width={1150}
                            data={this.state.wc}
                            fontSizeMapper={fontSizeMapper}
                        />
                    </Col>
                </Row>
            </Layout>
        )
    }
}