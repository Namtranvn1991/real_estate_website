import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RealDeleteComponent } from './real-delete.component';

describe('RealDeleteComponent', () => {
  let component: RealDeleteComponent;
  let fixture: ComponentFixture<RealDeleteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RealDeleteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RealDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
